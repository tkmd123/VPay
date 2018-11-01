import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WalletTransctionType from './wallet-transction-type';
import WalletTransctionTypeDetail from './wallet-transction-type-detail';
import WalletTransctionTypeUpdate from './wallet-transction-type-update';
import WalletTransctionTypeDeleteDialog from './wallet-transction-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WalletTransctionTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WalletTransctionTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WalletTransctionTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={WalletTransctionType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={WalletTransctionTypeDeleteDialog} />
  </>
);

export default Routes;
