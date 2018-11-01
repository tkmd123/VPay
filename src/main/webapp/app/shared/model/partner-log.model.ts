import { IPartner } from 'app/shared/model//partner.model';

export interface IPartnerLog {
  id?: number;
  partner?: IPartner;
}

export const defaultValue: Readonly<IPartnerLog> = {};
