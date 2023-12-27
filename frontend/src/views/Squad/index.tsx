

import React, { useState,useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './style.css';

const api = axios.create({
    baseURL: 'http://localhost:8000', // 서버의 baseURL 설정
});



const SearchComponent: React.FC = () => {
    const [gameName, setGameName] = useState<string>('');
    const [tagLine, setTagLine] = useState<string>('');
    const navigate = useNavigate();


    const handleSearch = async () => {

        try {
            const existingData = await api.post('/search-service/v1/summoner/get', {
                gameName,
                tagLine,
            });

            if (existingData.data && existingData.data.id) {
                navigate(`/summoner/${gameName}/${tagLine}`);
            } else {
                const createResponse = await api.post('/search-service/v1/match/create', {
                    gameName,
                    tagLine,
                });
                if (createResponse.data && createResponse.data.id) {
                    navigate(`/summoner/${gameName}/${tagLine}`);
                }
            }
        } catch (error) {

                console.error('Error while searching:', error);
            }
        }



    return (
        <div id='Squad'>
            <div className="BannerImg" />
            <div className="MainSearchContainer">
                <input
                    type="text"
                    value={gameName}
                    onChange={(e) => setGameName(e.target.value)}
                    placeholder="닉네임을 입력하세요"
                />
                <input
                    type="text"
                    value={tagLine}
                    onChange={(e) => setTagLine(e.target.value)}
                    placeholder="태그를 입력하세요"
                 />
                <button onClick={handleSearch}>검색</button>

            </div>
            </div>
    );
};

export default SearchComponent;

