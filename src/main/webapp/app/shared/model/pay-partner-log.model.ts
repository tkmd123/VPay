import { IPayPartner } from 'app/shared/model//pay-partner.model';

export interface IPayPartnerLog {
  id?: number;
  payPartner?: IPayPartner;
}

export const defaultValue: Readonly<IPayPartnerLog> = {};
