import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CurrentReading from './current-reading';
import CurrentReadingDetail from './current-reading-detail';
import CurrentReadingUpdate from './current-reading-update';
import CurrentReadingDeleteDialog from './current-reading-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CurrentReadingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CurrentReadingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CurrentReadingDetail} />
      <ErrorBoundaryRoute path={match.url} component={CurrentReading} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CurrentReadingDeleteDialog} />
  </>
);

export default Routes;
