import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import {fetchPersonalitiesApi} from "../../../apis";

const ViewPersonalities: React.FC = () => {
    const [cookies] = useCookies(['accessToken']);
    const [personalities, setPersonalities] = useState<string[]>([]);

    useEffect(() => {
        const fetchPersonalities = async () => {
            const token = cookies.accessToken;

            if (!token) {
                console.error('No access token available');
                return;
            }

            try {
                const personalities = await fetchPersonalitiesApi(token);
                if (personalities) {
                    setPersonalities(personalities);
                }
            } catch (error) {
                // Error handling is already done in fetchPersonalitiesApi
                // Additional UI-based error handling can be added here if needed
            }
        };

        fetchPersonalities();
    }, [cookies]); // Dependency array with cookies to re-run the effect if cookies change


    return (
        <div>
            <ul>
                {personalities.map((personality, index) => (
                    <li className="mt-2 text-lg font-bold text-gray-900 dark:text-gray-100" key={index}>#{personality}</li>
                ))}
            </ul>
        </div>
    );
};

export default ViewPersonalities;
