import { IWallet } from 'app/shared/model//wallet.model';
import { IWalletRule } from 'app/shared/model//wallet-rule.model';
import { IPayPartnerLog } from 'app/shared/model//pay-partner-log.model';

export interface IPayPartner {
  id?: number;
  payPartnerCode?: string;
  payPartnerName?: string;
  payParterDesc?: string;
  payPartners?: IWallet[];
  payPartners?: IWalletRule[];
  payPartners?: IPayPartnerLog[];
}

export const defaultValue: Readonly<IPayPartner> = {};
