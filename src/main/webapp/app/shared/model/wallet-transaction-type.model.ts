import { IWalletTransaction } from 'app/shared/model//wallet-transaction.model';

export interface IWalletTransactionType {
  id?: number;
  walletTransTypeCode?: string;
  walletTransTypeName?: string;
  walletTransTypeDesc?: string;
  walletTransTypeFlag?: number;
  walletTransTypeUDF1?: string;
  walletTransTypeUDF2?: string;
  walletTransTypeUDF3?: string;
  walletTransTypes?: IWalletTransaction[];
}

export const defaultValue: Readonly<IWalletTransactionType> = {};
