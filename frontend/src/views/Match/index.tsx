import React, { useEffect, useState } from "react";
import axios from 'axios';
import Modal from "../../components/ModalWrite";
import UserInfoStore from "../../stores/userInfo.store";
import {useNavigate} from "react-router-dom";

interface BoardListResponseDto {
    myPos: string;
    wantPos: string;
    useMic: boolean;
    summonerName: string;
    tagLine: string | null;
    rank: string;
    memo: string;
    commentList: any[] | null;
    updatedAt: string;
    qtype: string;
    sid: number;
}
export default function Match() {
    const { userInfo } = UserInfoStore();

    const [isModalOpen, setModalOpen] = useState(false);
    const [currentList, setCurrentList] = useState<BoardListResponseDto[]>([]);

    const openModal = () => setModalOpen(true);
    const closeModal = () => setModalOpen(false);


    const navigate = useNavigate();

    const handleBoardItemClick = (sId: any) => {
        navigate(`/board/${sId}`);
    }
    useEffect(() => {
        const fetchSquads = async () => {
            try {
                const response = await axios.get('http://localhost:8000/v1/squads');
                setCurrentList(response.data);
            } catch (error) {
                console.error('Error fetching squads data:', error);
                // Handle the error appropriately
            }
        };
        fetchSquads(); // Call immediately on component mount


        const intervalId = setInterval(() => {
            fetchSquads();
        }, 5000);

        return () => clearInterval(intervalId); //
    }, []); // Empty dependency array ensures this effect runs once after the component mounts

    return (
        <div>
            <div>
                <div>{userInfo?.lolNickname}</div>
                <button onClick={openModal}>Write</button>
                {isModalOpen && <Modal onClose={closeModal} />}

                {/* Example of rendering the fetched data */}
                <div>
                    <hr />

                    {currentList.map((item, index) => (
                        <div key={index} onClick={() => handleBoardItemClick(item.sid)}>
                            <div>Summoner Name: {item.summonerName}</div>
                            <div>Rank: {item.rank}</div>
                            <div>My Position: {item.myPos}</div>
                            <div>Want Position: {item.wantPos}</div>
                            <div>Use Mic: {item.useMic ? 'Yes' : 'No'}</div>
                            <div>Qtype: {item.qtype}</div>
                            <div>TagLine: {item.tagLine}</div>
                            <div>Memo: {item.memo}</div>
                            <div>CommentList: {item.commentList}</div>
                            <div>UpdatedAt: {item.updatedAt}</div>
                            <div>Sid: {item.sid}</div>
                            <hr />
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}
