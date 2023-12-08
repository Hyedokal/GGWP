import React, {useState} from "react";
import Modal from "../../components/ModalWrite";
import UserInfoStore from "../../stores/userInfo.store";

export default function Match(){
    //유저 인포
    const {userInfo} = UserInfoStore();

    const [isModalOpen, setModalOpen] = useState(false);

    const openModal = () => setModalOpen(true);
    const closeModal = () => {
        setModalOpen(false);
    };


    //해야할것
    //1. 모달창에서 글을 쓸떄  유저 이름부분에 로그인한 유저의 이름이 들어가게 하기
    //2. 옆에 태그도 추가
    //3. 글을 쓸때마다 화면에 바로바로 뜨게 하기



    return(
        <div>
            <div>

                <div>{userInfo?.lolNickname}</div>
                <button onClick={openModal}>Write</button>
                {isModalOpen && <Modal onClose={closeModal} />}
            </div>
        </div>
    )
}