import React, {useEffect, useState} from 'react';
import { getPlayers } from './services/playerService';

const PlayerList = () => {

    const [players, setPlayers] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        const fetchPlayers = async () => {

            const data = await getPlayers();

            setPlayers(data);
            setLoading(false);
        };

        fetchPlayers();
    }, []);

    if (loading) {

        return <p>Loading...</p>;

    }

    return (
    
        <div>
            <h1>Player List</h1>
            <ul>
                {players.map((player) => (
                    <li key={player.name}> {player.name} - {player.position} </li>
                ))}
            </ul>
        </div>
    );
};

export default PlayerList;