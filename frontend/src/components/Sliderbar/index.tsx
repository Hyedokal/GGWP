import './style.css';
import React, { useEffect, useState} from "react";
import UserInfoStore from "../../stores/userInfo.store";
import useCidInfoStore from "../../stores/cid.store";
import axios from "axios";
import {Client} from "@stomp/stompjs/esm6";
import {Message} from "../../interface/Message";

interface NoticeResponse {
    statusCode: number;
    data: string;
}

const SliderBar: React.FC<{ messages: Message[], sendMessage: (msg: string) => void }> = ({ messages, sendMessage }) => {
    const [isVisible, setIsVisible] = useState(false);
    const [stompClient, setStompClient] = useState<Client | null>(null);
    const { userInfo } = UserInfoStore();
    const [noticeResponse, setNoticeResponse] = useState<NoticeResponse | null>(null);
    const{cidInfo, setCidInfo} = useCidInfoStore();
    const toggleSlider = () => {
        setIsVisible(!isVisible);
    };
    const [isNewMessage, setIsNewMessage] = useState(false);
    const [hiddenMessages, setHiddenMessages] = useState(new Set());
    const [showAlarmBackground, setShowAlarmBackground] = useState(false);
    const [isShaking, setIsShaking] = useState(false);

    useEffect(() => {
        if (isNewMessage) {
            setIsShaking(true);
        }
    }, [isNewMessage]);

    const handleAlarmClick = () => {
        toggleSlider();
        setIsShaking(false); // Stop shaking when the alarm is clicked
    };


    const hideMessage = (index: number) => {
        setHiddenMessages(currentHidden => new Set(currentHidden).add(index));
    };

    useEffect(() => {
        if (messages.length > 0) {
            setShowAlarmBackground(true);
        }
    }, [messages]);


    useEffect(() => {
        if (cidInfo?.cid) {
            axios.post('http://localhost:8000/notice-service/v1/notice/create', {
                cid: cidInfo.cid
            })
                .then(response => {
                    setNoticeResponse(response.data);
                })
                .catch(error => {
                    console.error('Error creating notice:', error);
                });
        }
    }, [cidInfo]); // Add sendMessage as a dependency


    useEffect(() => {
        if (messages.length > 0) {
            setIsNewMessage(true);

            // Reset the state after some time
            const timeoutId = setTimeout(() => {
                setIsNewMessage(false);
            }, 2000); // Adjust time as needed

            return () => {
                clearTimeout(timeoutId);
            };
        }
    }, [messages]);


    return (
        <div>

            <img
                src="https://img.icons8.com/ios/50/000000/alarm.png"
                alt="Alarm Icon"
                onClick={handleAlarmClick}
                className={isShaking ? 'shake' : ''}
            />
            <div className={`slider-container ${isVisible ? 'visible' : ''}`}>
                <div>
                    <h2>Messages</h2>
                    <ul>
                        {messages.map((message, index) => (
                            hiddenMessages.has(index) ? null : (
                                <li key={index} onClick={() => hideMessage(index)}>
                                    {message.senderName}: {message.msg}
                                </li>
                            )
                        ))}
                    </ul>
                </div>
            </div>

        </div>
    );
}


export default SliderBar;