import React from 'react';

interface ListProps {
    data: any[];
}

const List: React.FC<ListProps> = ({ data }) => {
    return (
        <div className="listview">
            <ul>
                {data.map((item, index) => (
                    <li key={index}>{item}</li>
                ))}
            </ul>
        </div>
    );
};

export default List;