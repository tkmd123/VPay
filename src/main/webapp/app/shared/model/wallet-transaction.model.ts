import { Moment } from 'moment';
import { IWalletTransactionType } from 'app/shared/model//wallet-transaction-type.model';
import { IWallet } from 'app/shared/model//wallet.model';
import { IPartnerTransaction } from 'app/shared/model//partner-transaction.model';

export interface IWalletTransaction {
  id?: number;
  walletTransAmount?: number;
  walletTransDate?: Moment;
  walletTransRef?: string;
  walletTransUsername?: string;
  walletTransUDF1?: string;
  walletTransUDF2?: string;
  walletTransUDF3?: string;
  walletTransactionType?: IWalletTransactionType;
  wallet?: IWallet;
  walletTransactions?: IPartnerTransaction[];
}

export const defaultValue: Readonly<IWalletTransaction> = {};
