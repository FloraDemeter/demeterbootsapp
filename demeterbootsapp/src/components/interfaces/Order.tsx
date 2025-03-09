export interface Order {
    id: string;
    customerID: string;
    orderDate: string;
    predictedFinishDate: string;
    location: string;
    total: number;
    isWarrantyAccepted: boolean;
    status: number;
}

export interface OrderLine {
    id: string;
    orderID: string;
    productTypeID: number;
    leatherID: number;
    productStyle: string;
    price: number;
    notes: string;
}