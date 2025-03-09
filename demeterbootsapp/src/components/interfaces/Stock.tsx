export interface Leather {
    id: number;
    colour: string;
    description: string;
    imagePath: string;
    isAvailable: boolean;
}

export interface ProductStyle {
    id: number;
    productStyleTypeID: number
    description: string;
}

export interface ProductStyleType {
    id: number;
    description: string;
}

export interface ProductType {
    id: number;
    description: string;
    price: number;
}

export interface RepairCategory {
    id: number;
    productTypeId: number;
    description: string;
    price: number;
}