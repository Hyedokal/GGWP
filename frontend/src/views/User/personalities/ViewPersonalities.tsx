import React, { useState, useEffect } from 'react';
import {fetchPersonalitiesApi} from "../../../apis";
import UserInfoStore from "../../../stores/userInfo.store";

const ViewPersonalities: React.FC = () => {
    const [personalities, setPersonalities] = useState<string[]>([]);

    const {userInfo} = UserInfoStore();
    const lolNick = userInfo?.lolNickname;
    const tag = userInfo?.tag;

    useEffect(() => {
        const fetchPersonalities = async () => {

            try {
                const personalities = await fetchPersonalitiesApi(lolNick, tag);
                if (personalities) {
                    setPersonalities(personalities);
                }
            } catch (error) {
            }
        };

        fetchPersonalities();
    }, []); // Empty dependency array if lolNick and tag do not change over time


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
