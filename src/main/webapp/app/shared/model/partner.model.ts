import { IWallet } from 'app/shared/model//wallet.model';
import { IPartnerLog } from 'app/shared/model//partner-log.model';
import { IPartnerTransaction } from 'app/shared/model//partner-transaction.model';

export interface IPartner {
  id?: number;
  partnerCode?: string;
  partnerName?: string;
  partnerDesc?: string;
  partnerOrder?: number;
  partnerAPIUsername?: string;
  partnerAPIPassword?: string;
  partnerUDF1?: string;
  partnerUDF2?: string;
  partnerUDF3?: string;
  partners?: IWallet[];
  partners?: IPartnerLog[];
  partners?: IPartnerTransaction[];
}

export const defaultValue: Readonly<IPartner> = {};
