import { IWalletTransctionType } from 'app/shared/model//wallet-transction-type.model';
import { IWalletAllotment } from 'app/shared/model//wallet-allotment.model';

export interface IWalletTransaction {
  id?: number;
  walletTransctionType?: IWalletTransctionType;
  walletAllotment?: IWalletAllotment;
}

export const defaultValue: Readonly<IWalletTransaction> = {};
