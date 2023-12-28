import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const SearchComponent = () => {
    const [gameName, setGameName] = useState('');
    const [tagLine, setTagLine] = useState('');
    const navigate = useNavigate();

    const handleSearch = async () => {
        try {
            const result = await axios.post('http://localhost:8000/search-service/v1/account/get', {
                gameName,
                tagLine,
            });

            if (result.status === 200) {
                // Navigate to the profile viewer route on success
                navigate(`/summoner/${gameName}/${tagLine}`);
            }
        } catch (error) {
            console.error('Error in POST request:', error);
        }
    };

    return (
        <div className="mt-4 bg-white p-4 rounded-full shadow-md w-[80%] max-w-[600px] flex items-center justify-between">
            <input
                type="text"
                value={gameName}
                onChange={(e) => setGameName(e.target.value)}
                className="flex h-10 w-full rounded-md border border-input bg-background text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 flex-grow px-4 py-2"
                placeholder="Search game records"
            />
            <input
                type="text"
                value={tagLine}
                onChange={(e) => setTagLine(e.target.value)}
                className="flex h-10 w-full rounded-md border border-input bg-background text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 flex-grow px-4 py-2"
                placeholder="Enter Tag Line"
            />
            <button
                onClick={handleSearch}
                className="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2 ml-4">
                Search
            </button>
        </div>
    );
};

export default SearchComponent;