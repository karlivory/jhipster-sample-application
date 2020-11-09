import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Reading from './reading';
import ReadingDetail from './reading-detail';
import ReadingUpdate from './reading-update';
import ReadingDeleteDialog from './reading-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ReadingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ReadingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ReadingDetail} />
      <ErrorBoundaryRoute path={match.url} component={Reading} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ReadingDeleteDialog} />
  </>
);

export default Routes;
