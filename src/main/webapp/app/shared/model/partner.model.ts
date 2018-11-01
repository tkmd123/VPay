import { IWalletAllotment } from 'app/shared/model//wallet-allotment.model';
import { IPartnerLog } from 'app/shared/model//partner-log.model';

export interface IPartner {
  id?: number;
  partnerCode?: string;
  partnerName?: string;
  partnerDesc?: string;
  partnerOrder?: number;
  partners?: IWalletAllotment[];
  partners?: IPartnerLog[];
}

export const defaultValue: Readonly<IPartner> = {};
