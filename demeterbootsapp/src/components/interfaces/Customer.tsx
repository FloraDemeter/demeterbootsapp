export interface Customer {
  id: string;
  firstName: string;
  lastName: string;
  street: string;
  postCode: string;
  city: string;
  email: string;
  phone: string;
}

export interface Measurements {
  id: string;
  customerID: string;
  date: Date;
  notes: string;
  feet: number;
  bunion: number;
  highPoint: number;
  heel: number;
  ankle: number;
  calf: number;
  underKnee: number;
  height: number;
  calfHeight: number;
  tMark: number;
  imagePath: string;
}