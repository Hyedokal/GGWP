import React from 'react';
import { Link } from 'react-router-dom';


const Header = () => {

    const ovalStyle = {
        width: '6vw', // 텍스트의 길이에 따라서 여유 공간을 더하여 타원 크기를 설정합니다
        height: '4vh', // 타원의 높이
        borderRadius: '50%', // 타원 형태로 만듭니다
        backgroundColor: '#4B7C8C',
        color: '#fff',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
            fontSize:'0.8vw',
        position:'absolute',
        top:'',
        left:'50%'

    };
    const ovalStyle2 = {
        width: '6vw', // 텍스트의 길이에 따라서 여유 공간을 더하여 타원 크기를 설정합니다
        height: '4vh', // 타원의 높이
        borderRadius: '50%', // 타원 형태로 만듭니다
        backgroundColor: '#4B7C8C',
        color: '#fff',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        fontSize:'0.8vw',
        position:'absolute',
        top:'',
        left:'40%'

    };
    const ovalStyle3 = {
        width: '7vw', // 텍스트의 길이에 따라서 여유 공간을 더하여 타원 크기를 설정합니다
        height: '4vh', // 타원의 높이
        borderRadius: '50%', // 타원 형태로 만듭니다
        backgroundColor: '#4B7C8C',
        color: '#fff',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        fontSize:'0.8vw',
        position:'absolute',
        top:'',
        left:'67%'

    };


    const firstImageStyle = {
        width: '80vw',
        height: '30vh',
        position: 'absolute',
        top: '10vh',
        left: '50%',
        transform: 'translateX(-50%)',
        zIndex: '2'
    };

    const secondImageStyle = {
        width: '10vw',
        height:'20vh',
        position: 'absolute',
        top: '-8vh',
        left: '23%',
        zIndex: '2'
    };

    const buttonGroupStyle = {
        margin: '2%',

        width: '20%',
        display:'flex',
        justifyContent:'space-around',
        textAlign: 'center'
    };


    const searchButton = {
        padding: '0.8vw', // 버튼 안쪽 여백을 화면 너비의 2%로 지정
        fontSize: '0.8vw', // 폰트 크기를 화면 너비의 1.5%로 지정
        backgroundColor: '#A0AAC5',
        border: 'none',
        borderRadius: '0.5rem',
        cursor: 'pointer',
        position: 'absolute',
        top: '0%', // 상단 위치 (상대적으로 50% 지점)
        left: '53%', // 좌측 위치 (상대적으로 50% 지점)
        transform: 'translate(-50%, -50%)'
         // 정확한 중앙 정렬을 위한 위치 조정
    };

    const matchButton = {
        padding: '0.8vw', // 버튼 안쪽 여백을 화면 너비의 2%로 지정
        fontSize: '0.8vw', // 폰트 크기를 화면 너비의 1.5%로 지정
        backgroundColor: '#A0AAC5',
        border: 'none',
        borderRadius: '0.5rem',
        cursor: 'pointer',
        position: 'absolute',
        top: '0%', // 상단 위치 (상대적으로 50% 지점)
        left: '43%', // 좌측 위치 (상대적으로 50% 지점)
        transform: 'translate(-50%, -50%)'
        // 정확한 중앙 정렬을 위한 위치 조정
    };
    const mypageButton = {
        padding: '0.8vw', // 버튼 안쪽 여백을 화면 너비의 2%로 지정
        fontSize: '0.8vw', // 폰트 크기를 화면 너비의 1.5%로 지정
        backgroundColor: '#A0AAC5',
        border: 'none',
        borderRadius: '0.5rem',
        cursor: 'pointer',
        position: 'absolute',
        top: '0%', // 상단 위치 (상대적으로 50% 지점)
        left: '70%', // 좌측 위치 (상대적으로 50% 지점)
        transform: 'translate(-50%, -50%)'
        // 정확한 중앙 정렬을 위한 위치 조정
    };


    return (
        <div >
            <div style={{ position: 'relative' }}>
                {/* 이미지 */}
                <img
                    src="/images/v1_396.png"
                    alt=""
                    style={firstImageStyle}
                />
                <img
                    src="/images/v1_999.png"
                    alt=""
                    style={secondImageStyle}
                />
                {/* 버튼 그룹 */}
                <div style={buttonGroupStyle}>
                    <Link to="/">
                        <button style={searchButton}>검색해요</button>
                        <button style={matchButton}>매칭해요</button>
                        <button style ={mypageButton}>내 정보</button>
                    </Link>
                </div>
                <div style ={ovalStyle}>#나의 전적확인</div>
                <div style ={ovalStyle2}>#나의 전적확인</div>
                <div style ={ovalStyle3}>#내정보#매칭내역</div>

                {/* 기타 컴포넌트 */}


            </div>
        </div>
    );
};

export default Header;
