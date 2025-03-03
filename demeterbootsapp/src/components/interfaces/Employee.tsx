export interface Employee {
    id: string;
    firstName: string;
    lastName: string;
    street: string;
    postCode: string;
    city: string;
    email: string;
    phone: string;
    username: string;
    password: string;
    isActive: boolean;
    accessLevel: number;
    startDate: string;
    endDate: string;
}

export interface Job {
    id: string;
    employeeID: string;
    status: number;
    taskID: string;
    taskType: string;
}

export interface JobNotifications {
    id: number;
    previousEmployeeID: string;
    newEmployeeID: string;
    isNotified: boolean;
    taskID: string;
    taskType: string;
}