import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WalletAllotment from './wallet-allotment';
import WalletAllotmentDetail from './wallet-allotment-detail';
import WalletAllotmentUpdate from './wallet-allotment-update';
import WalletAllotmentDeleteDialog from './wallet-allotment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WalletAllotmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WalletAllotmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WalletAllotmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={WalletAllotment} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={WalletAllotmentDeleteDialog} />
  </>
);

export default Routes;
