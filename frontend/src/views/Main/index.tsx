import './style.css';
import { useNavigate } from 'react-router-dom';
import {AUTH_PATH, MATCH_PATH, SEARCH_PATH, USER_PATH} from "../../constant";
import {useCookies} from "react-cookie";
import SearchComponent from "../Squad";

// component 인증화면 컴포넌트

export default function Main() {
    // render 인증화면 컴포넌트
    const [cookies] = useCookies();   //          state: cookie 상태          //

    const navigate = useNavigate(); // useNavigate 훅 사용
    const matchClick = () => {
        //토큰 체크
        //토큰이 있으면 매치 페이지로 이동
        //토큰이 없으면 로그인 페이지로 이동
        if (!cookies.accessToken) {
            alert('로그인 먼저 하세요');
            navigate(AUTH_PATH);
            return;
        }
        navigate(MATCH_PATH);
    };

    const searchClick=()=>{
        navigate(SEARCH_PATH);
    };

    const myPageClick=()=>{
        if (!cookies.accessToken) {
            alert('로그인 먼저 하세요');
            navigate(AUTH_PATH);
            return;
        }
        navigate(USER_PATH);
    }

    return(
        <div id='Main' >
            <div className='banner'> </div>
            <div id="main-contain" className="bg-[#6C7A89] h-screen flex flex-col items-center justify-center  ">
                <div className="main-text">GGWP 에서 검색하세요</div>
                <SearchComponent />

                <div className="mt-20 text-center">
                    <div className="flex items-center justify-center mb-2 mt-1">
                    </div>
                    <div className="grid grid-cols-3 gap-4">
                        <div className="rounded-lg border text-card-foreground w-[200px] bg-white shadow-lg"
                             data-v0-t="card">
                            <div className="p-6">
                                <div className="flex flex-col items-center" onClick={matchClick}>
                                    <div className="bg-gray-200 rounded-full w-[80px] h-[80px] mb-4"></div>
                                    <h3 className="text-sm font-bold">매치 하기</h3>
                                    <p className="text-xs text-gray-600 mt-2">상대팀과 승부를 겨뤄보세요 어떻게 될까요?</p>
                                </div>
                            </div>
                        </div>
                        <div className="rounded-lg border text-card-foreground w-[200px] bg-white shadow-lg"
                             data-v0-t="card">
                            <div className="p-6">
                                <div className="flex flex-col items-center" onClick={searchClick}>
                                    <div className="bg-gray-200 rounded-full w-[80px] h-[80px] mb-4"></div>
                                    <h3 className="text-sm font-bold">검색 하기</h3>
                                    <p className="text-xs text-gray-600 mt-2">나의 최근 경기를 확인해보세요 어땠을까요?</p>
                                </div>
                            </div>
                        </div>
                        <div className="rounded-lg border text-card-foreground w-[200px] bg-white shadow-lg"
                             data-v0-t="card">
                            <div className="p-6">
                                <div className="flex flex-col items-center">
                                    <div className="bg-gray-200 rounded-full w-[80px] h-[80px] mb-4" onClick={myPageClick}></div>
                                    <h3 className="text-sm font-bold">매칭 확인</h3>
                                    <p className="text-xs text-gray-600 mt-2">나의 매칭 내역을 확인해 보세요!</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </div>
    )
}