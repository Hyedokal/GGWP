import { FaBell } from "react-icons/fa";
import './App.css';
import React from "react"; // 스타일 파일 임포트

const MainPage = () =>{

    const headingText = {
        position:
            'absolute',
        top: '10%',
        left: '50%',

        transform: 'translateX(-50%)'
    }
    const textStyle = {
        fontSize: '1.5rem', // 반응형 폰트 크기
        color: 'black',
        position: 'absolute',
        top: '50vh', // 수직 중앙 정렬을 위해 상단 위치 조절
        left: '48%', // 가운데 정렬을 위한 왼쪽 위치 조절
        transform: 'translate(-50%, -50%)', // 가운데 정렬
        zIndex: '2',
    };
    return(
    <>
        <div style={textStyle}>
            <p>GGWP에서 할 수 있어요</p>

        </div>
        <div className="mainbox1">
            <div style = {headingText}>
                <p>매칭해요</p>
            </div>
        </div>
        <div className="mainbox2">
            <div style = {headingText}>
                <p>검색해요</p>
            </div>
        </div>
        <div className="mainbox3">
            <div style = {headingText}>
                <p>관리해요</p>
            </div>
        </div>
        <div className="number-box">
            <span className="number">1</span>
        </div>

        <div className="number-box2">
            <span className="number">2</span>
        </div>
        <div className="number-box3">
            <span className="number">3</span>
        </div>



        </>

    )
}
export default MainPage