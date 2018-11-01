import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WalletType from './wallet-type';
import Partner from './partner';
import PayPartner from './pay-partner';
import WalletTransctionType from './wallet-transction-type';
import Wallet from './wallet';
import WalletRule from './wallet-rule';
import WalletRuleRate from './wallet-rule-rate';
import WalletAllotment from './wallet-allotment';
import WalletTransaction from './wallet-transaction';
import PayPartnerLog from './pay-partner-log';
import PartnerLog from './partner-log';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/wallet-type`} component={WalletType} />
      <ErrorBoundaryRoute path={`${match.url}/partner`} component={Partner} />
      <ErrorBoundaryRoute path={`${match.url}/pay-partner`} component={PayPartner} />
      <ErrorBoundaryRoute path={`${match.url}/wallet-transction-type`} component={WalletTransctionType} />
      <ErrorBoundaryRoute path={`${match.url}/wallet`} component={Wallet} />
      <ErrorBoundaryRoute path={`${match.url}/wallet-rule`} component={WalletRule} />
      <ErrorBoundaryRoute path={`${match.url}/wallet-rule-rate`} component={WalletRuleRate} />
      <ErrorBoundaryRoute path={`${match.url}/wallet-allotment`} component={WalletAllotment} />
      <ErrorBoundaryRoute path={`${match.url}/wallet-transaction`} component={WalletTransaction} />
      <ErrorBoundaryRoute path={`${match.url}/pay-partner-log`} component={PayPartnerLog} />
      <ErrorBoundaryRoute path={`${match.url}/partner-log`} component={PartnerLog} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
