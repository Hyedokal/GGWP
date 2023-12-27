import React from 'react';

interface AlarmModalProps {
    isVisible: boolean;
    onClose: () => void;
}

const AlarmModal: React.FC<AlarmModalProps> = ({ isVisible, onClose }) => {
    if (!isVisible) return null;

    return (
        <div className="modal-backdrop">
            <div className="modal-content">
                <button onClick={onClose}>Close</button>
                {/* Add your modal content here */}
                <div>Alarm Content</div>
            </div>
        </div>
    );
};

export default AlarmModal;
