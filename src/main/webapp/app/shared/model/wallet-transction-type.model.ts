import { IWalletTransaction } from 'app/shared/model//wallet-transaction.model';

export interface IWalletTransctionType {
  id?: number;
  walletTransTypeCode?: string;
  walletTransTypeName?: string;
  walletTransTypeDesc?: string;
  walletTransTypeFlag?: number;
  walletTransTypes?: IWalletTransaction[];
}

export const defaultValue: Readonly<IWalletTransctionType> = {};
