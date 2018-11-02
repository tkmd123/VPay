import { IProduct } from 'app/shared/model//product.model';
import { IWallet } from 'app/shared/model//wallet.model';
import { IWalletRule } from 'app/shared/model//wallet-rule.model';
import { IPartnerTransaction } from 'app/shared/model//partner-transaction.model';

export interface IProductType {
  id?: number;
  productTypeCode?: string;
  productTypeName?: string;
  productTypeDesc?: string;
  productUDF1?: string;
  productUDF2?: string;
  productUDF3?: string;
  productTypes?: IProduct[];
  productTypes?: IWallet[];
  productTypes?: IWalletRule[];
  productTypes?: IPartnerTransaction[];
}

export const defaultValue: Readonly<IProductType> = {};
