import './style.css';
import { useNavigate } from 'react-router-dom';
import {MATCH_PATH, MY_PATH, SEARCH_PATH} from "../../constant";

// component 인증화면 컴포넌트

export default function Main() {
    // render 인증화면 컴포넌트

    const navigate = useNavigate(); // useNavigate 훅 사용
    const matchClick = () => {
        // 버튼을 클릭했을 때 이동할 링크를 여기에 넣어주세요
        navigate(MATCH_PATH);
    };

    const searchClick=()=>{
        navigate(SEARCH_PATH);
    };

    const mypageClick=()=>{
        navigate(MY_PATH);
    }

    return(
        <div id='Main'>
            <div className='banner'> </div>
            <div className='text-container'>

                <div className='img-container'>

                    <div className='beggwwp'>{'GGWP에서 할 수 있어요'}</div>
                </div>
                <div className='logo-container'>
                    {/*<div> <FaBell className="FaBell" /></div>*/}
                </div>
            </div>
            <div className='function-container'>



                <div className='match-box'>

                    <div className='matchbox-text-box' onClick={matchClick} >
                        <div className='matchbox-text'>{'매칭해요'}</div>
                    </div>
                    <div className="circle"></div>
                    <div className='matchbox-text-box2'>
                        사람들과 소통하며 게임해요
                    </div>
                </div>

                <div className='search-box'>
                    <div className='searchbox-text-box'onClick={searchClick}>
                        <div className='searchbox-text'>{'검색해요'}</div>
                    </div>
                    <div className="circle"></div>
                    <div className='matchbox-text-box2'>
                         나의 전적을 검색해요
                    </div>
                </div>

                <div className='mypage-box'>
                    <div className='mypagebox-text-box'onClick={mypageClick}>
                        <div className='mypagebox-text'>{'관리해요'}</div>
                    </div>
                    <div className="circle"></div>
                    <div className='matchbox-text-box2'>
                        내 매칭내역을 관리해요
                    </div>
                </div>
            </div>





        </div>
    )
}