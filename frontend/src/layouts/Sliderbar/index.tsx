import './style.css';
import {SetStateAction, useEffect, useState} from "react";
import UserInfoStore from "../../stores/userInfo.store";
import CidStore from "../../stores/cid.store";
import useCidInfoStore from "../../stores/cid.store";
import axios from "axios";
interface NoticeResponse {
    statusCode: number;
    data: string;
}

export default function SliderBar() {
    const [isVisible, setIsVisible] = useState(false);
    const [noticeResponse, setNoticeResponse] = useState<NoticeResponse | null>(null);

    const { userInfo, setUserInfo } = UserInfoStore();
    const{cidInfo, setCidInfo} = useCidInfoStore();

    const toggleSlider = () => {
        setIsVisible(!isVisible);
    };

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
    }, [cidInfo]);



    return (
        <div>
            <img
                src="https://img.icons8.com/ios/50/000000/alarm.png"
                alt="Alarm Icon"
                className="alarm-icon"
                onClick={toggleSlider}
            />
            <div className={`slider-container ${isVisible ? 'visible' : ''}`}>
                {cidInfo?.cid}
                {noticeResponse && (
                    <div>
                        <p>Status Code: {noticeResponse.statusCode}</p>
                        <p>Message: {noticeResponse.data}</p>
                    </div>
                )}
            </div>

        </div>
    );
}