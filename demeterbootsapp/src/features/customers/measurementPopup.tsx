import React from 'react';
import PopUp from '../../components/layout/popup';
import { Button } from '../../components/elements/button';
import Textfield from '../../components/elements/textfield';
import Textarea from '../../components/elements/textarea';

interface MeasurementPopUpProps {
    isPopUpOpen: boolean;
    setIsPopUpOpen: (isOpen: boolean) => void;
}

const MeasurementPopUp: React.FC<MeasurementPopUpProps> = ({isPopUpOpen, setIsPopUpOpen}) => {
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setIsPopUpOpen(false);
    }
    
    return (
        <PopUp isOpen={isPopUpOpen} popUpName="measurement-popup" onClose={() => setIsPopUpOpen(false)}>
            <h2> Add new measurement</h2>
            <form onSubmit={handleSubmit}>
                <div className="column-left">
                    <Textfield label="Feet" />
                    <Textfield label="Bunion" />
                    <Textfield label="Highest Point" />
                    <Textfield label="Heel" />
                    <Textfield label="Ankle" />
                </div>
                <div className="column-right">
                    <Textfield label="Calf" />
                    <Textfield label="Under Knee" />
                    <Textfield label="Height" />
                    <Textfield label="Calf Height" />
                    <Textfield label="20cm Mark" />
                </div>
                <div className="full-width">
                    <Textarea label="Notes" />
                    <Button type="submit" variant="primary">Save</Button>
                </div>
            </form>
        </PopUp>
    )
};

export default MeasurementPopUp;