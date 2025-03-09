export interface Invoice {
    id: string;
    customerId: string;
    status: number;
    paymentType: number;
    total: number;
    invoiceDate: string;
    paymentDate: string;
    isPaid: boolean;
}

export interface InvoiceLine {
    id: String;
    invoiceID: string;
    taskId: string;
    taskType: string;
    total: number;
}