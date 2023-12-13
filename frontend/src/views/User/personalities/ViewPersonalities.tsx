import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useCookies } from 'react-cookie';

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
                const response = await axios.get('http://localhost:8000/member-service/v1/member/personalities', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (response.data && response.data.personalities) {
                    setPersonalities(response.data.personalities);
                }
            } catch (error) {
                console.error('There was an error fetching the personalities:', error);
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
