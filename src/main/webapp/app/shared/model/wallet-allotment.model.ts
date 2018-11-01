import { IPartner } from 'app/shared/model//partner.model';
import { IWallet } from 'app/shared/model//wallet.model';
import { IWalletTransaction } from 'app/shared/model//wallet-transaction.model';

export interface IWalletAllotment {
  id?: number;
  walletAllotmentAmount?: number;
  partner?: IPartner;
  wallet?: IWallet;
  walletAllotments?: IWalletTransaction[];
}

export const defaultValue: Readonly<IWalletAllotment> = {};
