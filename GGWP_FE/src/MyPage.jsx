// MyPage.jsx

import React from 'react';
import './App.css'; // 스타일 파일 임포트
import { useNavigate } from 'react-router-dom';
import { FaBell } from "react-icons/fa";

const MyPage = () => {
    const navigate = useNavigate(); // useNavigate 훅 사용

    const modifyClick = () => {
        // 버튼을 클릭했을 때 이동할 링크를 여기에 넣어주세요
        navigate('/your-link-here');
    };
    const withdrawClick = () =>{
        navigate('/withdrawlink');
    }
    return (
        <div className="my-page">
            <div className="image-container">
                <img
                    src="/images/v1_999.png"
                    alt="이미지 설명"
                    className="custom-image"
                />
            </div>
            <div className="mypage">
                <div className="custom-box">
                    <p>마이페이지</p>
                </div>
            </div>
            <div className="matchinglist-box">
            </div>
            <div className="matchinglist-box2">
            </div>
            <div className="matchinglist-box3">
            </div>
            <button className = "modify-box" onClick={modifyClick}> 수정 </button>
            <button className = "withdraw-box" onClick={withdrawClick}> 탈퇴 </button>

            <div className="rounded-rectangle">
                <p>빡겜</p>
                <p>직장인</p>
            </div>
            <div> <FaBell className="FaBell" /></div>

            {/* 다른 컴포넌트들 */}
        </div>
    );
};

export default MyPage;
