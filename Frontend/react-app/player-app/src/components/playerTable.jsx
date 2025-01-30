import React, {useEffect, useState} from 'react';
import { getPlayers } from '../services/playerService';
import ReactPaginate from 'react-paginate';
import '../styles/PlayerTable.css';

const PlayerTable = () => {

    const [players, setPlayers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(1);
    const [playersPerPage, setPlayersPerPage] = useState(12);

    useEffect(() => {

        const fetchPlayers = async () => {

            const data = await getPlayers();

            setPlayers(data || []);
            setLoading(false);
        };

        fetchPlayers();
    }, []);

    if (loading) {

        return <p>Loading...</p>;
    }

    const indexOfLastPlayer = currentPage * playersPerPage;
    const indexOfFirstPlayer = indexOfLastPlayer - playersPerPage;
    const currentPlayers = players.slice(indexOfFirstPlayer, indexOfLastPlayer);
  

    const handlePageClick = (event) => {
        setCurrentPage(event.selected + 1);
    };

    return (
        <div>
            <h2>Player Info</h2>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Position</th>
                        <th>Team</th>
                    </tr>
                </thead>
                <tbody>
                    {currentPlayers.map((player) => (
                        <tr key={player.id}>
                            <td>{player.name}</td>
                            <td>{player.position}</td>
                            <td>{player.team_name}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <ReactPaginate
                previousLabel={'Previous'}
                nextLabel={'Next'}
                breakLabel={'...'}
                pageCount={players.length > 0 ? Math.ceil(players.length / playersPerPage) : 1}
                marginPagesDisplayed={2}
                pageRangeDisplayed={3}
                onPageChange={(event) => {
                    console.log('Page clicked', event.selected + 1); // Check if the event is firing
                    handlePageClick(event);
                }}
                containerClassName={'pagination'}
                activeClassName={'active'}
            />
        </div>
    );
};

export default PlayerTable;