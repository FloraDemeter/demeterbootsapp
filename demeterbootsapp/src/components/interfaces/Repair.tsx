export interface Repair {
    id: string;
    customerID: string;
    repairDate: string;
    predictedFinishDate: string;
    location: string;
    total: number;
    isWarrantyAccepted: boolean;
    status: number;
}

export interface RepairLine {
    id: string;
    repairID: string;
    repairCategoryID: number;
    price: number;
    notes: string;
}