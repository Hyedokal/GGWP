import React, {useState} from "react";
import Modal from "../../components/ModelWrite";

export default function Match(){
    const [isModalOpen, setModalOpen] = useState(false);

    const openModal = () => setModalOpen(true);
    const closeModal = () => {
        setModalOpen(false);
    };





    return(
        <div>
            <div>
                <button onClick={openModal}>Write</button>
                {isModalOpen && <Modal onClose={closeModal} />}
            </div>
        </div>
    )
}