import React from 'react';

interface PopUpProps {
    isOpen: boolean;
    onClose: () => void;
    children: React.ReactNode;
    popUpName: string;
}

const PopUp: React.FC<PopUpProps> = ({isOpen, onClose, children, popUpName}) => {
    if (!isOpen) return null;

    return (
        <div className="popup-overlay" onClick={onClose}>
            <div className="popup-content" id={popUpName} onClick={(e) => e.stopPropagation()}>
                <button className="close-button" onClick={onClose}>
                    &times;
                </button>
                {children}
            </div>
        </div>
    );
};

export default PopUp;