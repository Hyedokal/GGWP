
import './App.css';
import React from "react";
import {useNavigate} from "react-router-dom"; // 스타일 파일 임포트

const DetailPage = () => {
    const navigate = useNavigate();
    const handleNextButtonClick = () => {
        // 버튼을 클릭했을 때 이동할 링크를 여기에 넣어주세요
        navigate('/your-link-here');
    };
    const BackgroundRectangle = {
        backgroundColor: '#DFF9F7',
        width: '80vw',
        height: '50%',
        position: 'absolute',
        top: '45vh',
        left: '50%',
        transform: 'translateX(-50%)',
        zIndex: '1',
        borderRadius: '0.5rem',
    };


    const logoImage = {
        position: 'absolute',
        width: '20vw', // 뷰포트 너비의 10%
        height: '40vh', // 높이를 자동으로 조정하여 비율 유지
        top: '26vh', // 뷰포트 높이의 10%
        left: '13vw', // 뷰포트 너비의 30%
        zIndex: '2'
    }
    const textStyle = {
        fontSize: 'calc(1.0vw + 1vh)', // 반응형 폰트 크기
        color: 'black',
        position: 'absolute',
        top: '50vh', // 수직 중앙 정렬을 위해 상단 위치 조절
        left: '55%', // 가운데 정렬을 위한 왼쪽 위치 조절
        transform: 'translate(-50%, -50%)', // 가운데 정렬
        zIndex: '2',
    };
    const subtextStyle = {
        color:'black',
        position:'absolute',
        top:'55%',
        left:'40%',
        zIndex:'2'
    }
    const subtextStyle2 = {
        color:'black',
        position:'absolute',
        top:'65%',
        left:'38%',
        zIndex:'2'
    }


    const detailBoxStyle = {
        width: '20vw',
        height: '5vh',
        backgroundColor: '#A0AAC5',
        borderRadius: '10px',
        display: 'flex',
        alignItems: 'center',
        position: 'absolute',
        top: '60%',
        left: '60%',
        transform: 'translate(-50%, -50%)', // 중앙 정렬을 위한 transform 사용
        zIndex: '2'
    };

    const nextButtonStyle = {
        width: '10vw',
        height: '5vh',
        backgroundColor: '#A0AAC5',
        borderRadius: '0.6rem',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        position: 'absolute',
        top: '85%', // 상대적으로 조정하여 배치
        left: '60%', // 상대적으로 조정하여 배치
        transform: 'translate(-50%, -50%)', // 중앙 정렬을 위한 transform 사용
        zIndex: '2',
        cursor: 'pointer', // 커서 모양 변경
    };


    return(
        <>
        <div style={BackgroundRectangle}></div>
            <div style ={logoImage}>
                <img
                    src="/images/v1_999.png"
                    alt =" "
                />
            </div>
            <div style={textStyle}>
                <p>상세정보기입</p>


            </div>
            <div style={subtextStyle}>
                <p>롤닉네임</p>
            </div>
            <div style={subtextStyle2}>
                <p>나는 이런게이머에요</p>
            </div>
            <div style={detailBoxStyle}>

            </div>

            <div style={nextButtonStyle} onClick={handleNextButtonClick}>
                다음
            </div>

        </>
    )
}
export default DetailPage;