import React, { useEffect, useState, useMemo } from 'react';
import { getPlayers } from '../services/playerService';
import ReactPaginate from 'react-paginate';
import '../styles/PlayerTable.css';

const PlayerTable = () => {
    const [players, setPlayers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [playersPerPage, setPlayersPerPage] = useState(12);
    const [sortConfig, setSortConfig] = useState({sortKey: null, direction: 'asc'});
    const [filters, setFilters] = useState({
        name: '',
        position: '',
        teamName: ''
    });

    useEffect(() => {
        const fetchPlayers = async () => {
            const data = await getPlayers();
            const removedSquadTotal = data.filter(player => player.name !== 'Squad Total');
            setPlayers(removedSquadTotal || []);
            setLoading(false);
        };

        fetchPlayers();
    }, []);

    if (loading || players.length === 0) {
        return <p>Loading...</p>;
    }

    const positions = players.length ? Array.from(new Set(players.map(player => player.position))) : [];
    const teamNames = players.length ? Array.from(new Set(players.map(player => player.team_name))) : [];

    const filteredPlayers = players.filter(player => {
        return (
            (filters.name === "" || (player.name && player.name.toLowerCase().includes(filters.name.toLowerCase()))) &&
            (filters.position === "" || (player.position && player.position.toLowerCase().includes(filters.position.toLowerCase()))) &&
            (filters.teamName === "" || (player.team_name && player.team_name.toLowerCase().includes(filters.teamName.toLowerCase())))
        );
    });

    const sortPlayers = (players, sortConfig) => {
        if (!sortConfig.sortKey) return players;

        return players.sort((a, b) => {
            const valueA = a[sortConfig.sortKey];
            const valueB = b[sortConfig.sortKey];

            let comparison = 0;
            if (typeof valueA === 'string' && typeof valueB === 'string') {
                comparison = valueA.localeCompare(valueB);
            } else {
                comparison = valueA - valueB;
            }

            return sortConfig.direction === 'asc' ? comparison : -comparison;
        });
    };

    const filteredSortedPlayers = sortPlayers(filteredPlayers, sortConfig);
    
    const indexOfLastPlayer = currentPage * playersPerPage;
    const indexOfFirstPlayer = indexOfLastPlayer - playersPerPage;
    const currentPlayers = filteredSortedPlayers.slice(indexOfFirstPlayer, indexOfLastPlayer);

    const handlePageClick = (event) => {
        setCurrentPage(event.selected + 1);
    };

    const handleSort = (sortKey) => {

        setSortConfig(prev => ({
            sortKey,
            direction : prev.sortKey === sortKey && prev.direction === 'asc' ? 'desc' : 'asc'
        }));
    };

    const resetFilters = () => {
        setFilters({
            name: "",
            position: "",
            teamName: ""
        });
    };

    return (
        <div>
            <h2>Player Table</h2>
            <div>
                <select 
                    onChange={e => setFilters(prev => ({ ...prev, position: e.target.value }))}
                    value={filters.position}
                >
                    <option value="">All Positions</option>
                    {positions.map(position => (
                        <option key={position} value={position}>{position}</option>
                    ))}
                </select>
                <select
                    onChange={e => setFilters(prev => ({ ...prev, teamName: e.target.value}))}
                    value={filters.teamName}
                >
                    <option value="">All Teams</option>
                    {teamNames.map(team => (
                        <option key={team} value={team}>{team}</option>
                    ))}
                </select>
                <input 
                    type="text" 
                    placeholder="Search by name" 
                    value={filters.name}
                    onChange={e => setFilters(prev => ({ ...prev, name: e.target.value}))}
                />
                <button onClick={resetFilters}>Clear Filters</button>
            </div>
            <table>
                <thead>
                    <tr>
                        <th onClick = {() => handleSort('name')}>
                            Name 
                            <span className = {sortConfig.sortKey === 'name' && sortConfig.direction === 'asc' ? 'active' : ''}>▲</span>
                            <span className = {sortConfig.sortKey === 'name' && sortConfig.direction === 'desc' ? 'active' : ''}>▼</span>
                        </th>
                        <th onClick = {() => handleSort('position')}>
                            Position
                            <span className = {sortConfig.sortKey === 'position' && sortConfig.direction === 'asc' ? 'active' : ''}>▲</span>
                            <span className = {sortConfig.sortKey === 'position' && sortConfig.direction === 'desc' ? 'active' : ''}>▼</span>
                        </th>
                        <th onClick = {() => handleSort('team_name')}>
                            Team
                            <span className = {sortConfig.sortKey === 'team_name' && sortConfig.direction === 'asc' ? 'active' : ''}>▲</span>
                            <span className = {sortConfig.sortKey === 'team_name' && sortConfig.direction === 'desc' ? 'active' : ''}>▼</span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {currentPlayers.map(player => ( 
                        <tr key={player.id}>
                            <td>{player.name}</td>
                            <td>{player.position}</td>
                            <td>{player.team_name}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <ReactPaginate
                previousLabel={"Previous"}
                nextLabel={"Next"}
                breakLabel={"..."}
                pageCount={Math.ceil(filteredSortedPlayers.length / playersPerPage)}
                marginPagesDisplayed={2}
                pageRangeDisplayed={3}
                onPageChange={handlePageClick}
                containerClassName={"pagination"}
                activeClassName={"active"}
            />
        </div>
    );
};

export default PlayerTable;