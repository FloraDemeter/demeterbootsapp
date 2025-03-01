import React from 'react';
import PopUp from './popup';
import { Button } from '../elements/button';
import { useParams, useSearchParams } from "react-router-dom";
import RadioButtonGroup from '../elements/radioinput';


interface ConfirmPopUpProps {
    isPopUpOpen: boolean;
    setIsPopUpOpen: (isOpen: boolean) => void;
    purpose ?: "confirmation" | "warning" | "delete",
    text: string,
}

const ConfirmPopUp: React.FC<ConfirmPopUpProps> = ({isPopUpOpen, setIsPopUpOpen, purpose, text}) => {
    const buttonText = (purpose !== "warning") ? "Yes" : "Ok";

    const cancel = () => {
        setIsPopUpOpen(false);
    }

    const confirm = () => {
        setIsPopUpOpen(false);
        //if customer it will redirect to the user selected screen with filtered data
    }

    const { contentType } = useParams<{ contentType?: string }>();
    const [searchParams] = useSearchParams();
    const viewParam = searchParams.has("view");
    const viewID = searchParams.get("view");

    return (
        <PopUp isOpen={isPopUpOpen} popUpName="orderLine-popup" onClose={() => setIsPopUpOpen(false)}>
            <div>
                {contentType !== "customer" ? (
                    <div className="image-text">
                        {purpose === "confirmation" ? (
                            contentType !== "customer" ? <img src='../../../resources/confirm.png' alt="confirm"></img> : null
                        ) : purpose === "warning" ? (
                            <img src='../../../resources/warning.png' alt="warning"></img>
                        ): (
                            <img src='../../../resources/delete.png' alt="delete"></img>
                        )}
                        <p>{text}</p>
                    </div>
                ): (
                    <div className="confirm-options">
                        <p>{text}</p>
                        <RadioButtonGroup 
                            name="show"
                            options={[
                                { value: "order", label: "Orders" },
                                { value: "repair", label: "Repairs" },
                                { value: "invoice", label: "Invoices" }
                            ]}
                        />
                    </div>
                )}
                <div className="two-buttons">
                    <Button variant="secondary" onClick={cancel}>Cancel</Button>
                    <Button variant="primary" onClick={confirm}>{buttonText}</Button>
                </div>
            </div>
        
        </PopUp>
    )
}

export default ConfirmPopUp;