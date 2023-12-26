import axios from 'axios';
import { useCookies } from "react-cookie";
import { useState } from "react";
import {postPersonalitiesApi} from "../../../apis";

export interface InputValuesType {
    personality1: string;
    personality2: string;
    personality3: string;
}

const RequestSender: React.FC = () => {
    const [cookies] = useCookies(['accessToken']); // accessToken을 쿠키에서 가져옵니다.
    const token = cookies.accessToken; // 토큰에 접근합니다.
    const [modalIsOpen, setModalIsOpen] = useState(false); // 모달 열림 여부를 상태로 관리합니다.
    const [inputValues, setInputValues] = useState({
        personality1: "",
        personality2: "",
        personality3: ""
    }); // 인풋 박스의 값을 상태로 관리합니다.

    const openModal = () => {
        setModalIsOpen(true); // 모달을 열기 위해 상태를 변경합니다.
    };

    const closeModal = () => {
        setModalIsOpen(false); // 모달을 닫기 위해 상태를 변경합니다.
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setInputValues(prevState => ({
            ...prevState,
            [name]: value
        })); // 입력된 값을 상태에 업데이트합니다.
    };

    const sendRequest = async () => {
        try {
            const responseData = await postPersonalitiesApi(token, inputValues);
            console.log(responseData);
            closeModal();
            window.location.reload(); //page reload
        } catch (error) {
            // Error handling is already done in postPersonalitiesApi
            // Additional UI-based error handling can be added here if needed
        }
    };

    return (
        <div className="p-4">
            <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" onClick={openModal}>성격 추가</button>
            {modalIsOpen && (
                <div className="mt-4">
                    <input type="text" name="personality1" value={inputValues.personality1} onChange={handleInputChange} className="border border-gray-300 rounded-md px-2 py-1" />
                    <input type="text" name="personality2" value={inputValues.personality2} onChange={handleInputChange} className="border border-gray-300 rounded-md px-2 py-1 mt-2" />
                    <input type="text" name="personality3" value={inputValues.personality3} onChange={handleInputChange} className="border border-gray-300 rounded-md px-2 py-1 mt-2" />
                    <div className="mt-2">
                        <button className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mr-2" onClick={sendRequest}>전송</button>
                        <button className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded" onClick={closeModal}>닫기</button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default RequestSender;