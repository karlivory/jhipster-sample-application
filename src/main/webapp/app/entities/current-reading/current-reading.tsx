import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './current-reading.reducer';
import { ICurrentReading } from 'app/shared/model/current-reading.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICurrentReadingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CurrentReading = (props: ICurrentReadingProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { currentReadingList, match, loading } = props;
  return (
    <div>
      <h2 id="current-reading-heading">
        <Translate contentKey="jhipsterSampleApplicationApp.currentReading.home.title">Current Readings</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="jhipsterSampleApplicationApp.currentReading.home.createLabel">Create new Current Reading</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {currentReadingList && currentReadingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.currentReading.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.currentReading.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.currentReading.timestamp">Timestamp</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.currentReading.device">Device</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {currentReadingList.map((currentReading, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${currentReading.id}`} color="link" size="sm">
                      {currentReading.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`jhipsterSampleApplicationApp.ReadingType.${currentReading.type}`} />
                  </td>
                  <td>{currentReading.value}</td>
                  <td>
                    {currentReading.timestamp ? <TextFormat type="date" value={currentReading.timestamp} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{currentReading.device ? <Link to={`device/${currentReading.device.id}`}>{currentReading.device.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${currentReading.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${currentReading.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${currentReading.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterSampleApplicationApp.currentReading.home.notFound">No Current Readings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ currentReading }: IRootState) => ({
  currentReadingList: currentReading.entities,
  loading: currentReading.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CurrentReading);
