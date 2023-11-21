import React from 'react';
import {useNavigate} from 'react-router-dom';
const Log = ({ AUTH_URI }) => {
    const buttonStyle = {
        padding: '1vw', // 버튼 안쪽 여백을 화면 너비의 2%로 지정
        fontSize: '1vw', // 폰트 크기를 화면 너비의 1.5%로 지정
        backgroundColor: 'yellow',
        border: 'none',
        borderRadius: '0.5rem',
        cursor: 'pointer',
        position: 'absolute',
        top: '55%', // 상단 위치 (상대적으로 50% 지점)
        left: '63%', // 좌측 위치 (상대적으로 50% 지점)
        transform: 'translate(-50%, -50%)',
        zIndex: '2',
    };
    const adminButton = {
        padding:'1vw', // 버튼 안쪽 여백을 화면 너비의 2%로 지정
        fontSize: '1vw', // 폰트 크기를 화면 너비의 1.5%로 지정
        backgroundColor: 'gray',
        border: 'none',
        borderRadius: '0.5rem',
        cursor: 'pointer',
        position: 'absolute',
        top: '65%', // 상단 위치 (상대적으로 50% 지점)
        left: '63%', // 좌측 위치 (상대적으로 50% 지점)
        transform: 'translate(-50%, -50%)',
        zIndex: '2',

    };
    const navigate = useNavigate();
    const adminButtonHandler = () => {
        // 관리자 페이지로 이동하는 URL
        const adminDashboardLink = '/admin'; // 원하는 URL로 수정해주세요.

        // 페이지 이동
        navigate(adminDashboardLink);
    };


    const imageStyle = {
        width: '2vh', // 뷰포트 높이의 2%
        height: '2vh', // 뷰포트 높이의 2%
        marginRight: '1vh', // 뷰포트 높이의 1%
    };
    const logoImage = {
        position: 'absolute',
        width: '20vw', // 뷰포트 너비의 10%
        height: '40vh', // 높이를 자동으로 조정하여 비율 유지
        top: '26vh', // 뷰포트 높이의 10%
        left: '13vw', // 뷰포트 너비의 30%
        zIndex: '2'
    }

    const BackgroundRectangle = {
        backgroundColor: '#DFF9F7',
        width: '80vw',
        height: '35%',
        position: 'absolute',
        top: '45vh',
        left: '50%',
        transform: 'translateX(-50%)',
        zIndex: '1',
        borderRadius: '0.5rem',
    };

    const textStyle = {
        fontSize: 'calc(1.8vw + 1vh)', // 반응형 폰트 크기
        color: 'black',
        position: 'absolute',
        top: '50vh', // 수직 중앙 정렬을 위해 상단 위치 조절
        left: '45%', // 가운데 정렬을 위한 왼쪽 위치 조절
        transform: 'translate(-50%, -50%)', // 가운데 정렬
        zIndex: '2',
    };

    const subTextStyle = {
        fontSize: 'calc(1.2vw + 0.5vh)', // 반응형 폰트 크기
        color: 'black',
    };

    return (
        <div>
            <div style={BackgroundRectangle}></div>
            <div style ={logoImage}>
                <img
                    src="/images/v1_999.png"
                    alt =" "
                    />
            </div>
            <a href={AUTH_URI} >
                <button style = {adminButton} onClick = {adminButtonHandler}>관리자 계정 로그인</button>
                <button style={buttonStyle}>
                    <img
                        src="/images/v1_56.png"
                        alt="Kakao Logo"
                        style={imageStyle}
                    />
                    카카오로 시작하기
                </button>
            </a>
            <div style={{ position: 'relative' }}>
                <div style={textStyle}>
                    <p>Be GGWP OWN</p>
                    <p style={subTextStyle}>GOGOGO</p>
                </div>
            </div>
        </div>
    );
};

export default Log;
