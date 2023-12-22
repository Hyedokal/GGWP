import './style.css';
import {useState} from "react";

export default function SliderBar() {
    const [isVisible, setIsVisible] = useState(false);

    const [notification, setNotification] = useState('');

    // const showNotification = (message) => {
    //     setNotification(message);
    //     setIsVisible(true); // 알림과 함께 슬라이더를 자동으로 표시합니다.
    // };
    const toggleSlider = () => {
        setIsVisible(!isVisible);
    };

    return (
        <div>
            <img
                src="https://img.icons8.com/ios/50/000000/alarm.png"
                alt="Alarm Icon"
                className="alarm-icon"
                onClick={toggleSlider}
            />
            <div className={`slider-container ${isVisible ? 'visible' : ''}`}>


            </div>
        </div>
    );
}