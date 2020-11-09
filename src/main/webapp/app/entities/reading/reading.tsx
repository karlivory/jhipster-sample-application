import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './reading.reducer';
import { IReading } from 'app/shared/model/reading.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IReadingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Reading = (props: IReadingProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { readingList, match, loading } = props;
  return (
    <div>
      <h2 id="reading-heading">
        <Translate contentKey="jhipsterSampleApplicationApp.reading.home.title">Readings</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="jhipsterSampleApplicationApp.reading.home.createLabel">Create new Reading</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {readingList && readingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.reading.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.reading.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.reading.timestamp">Timestamp</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.reading.device">Device</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {readingList.map((reading, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${reading.id}`} color="link" size="sm">
                      {reading.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`jhipsterSampleApplicationApp.ReadingType.${reading.type}`} />
                  </td>
                  <td>{reading.value}</td>
                  <td>{reading.timestamp ? <TextFormat type="date" value={reading.timestamp} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{reading.device ? <Link to={`device/${reading.device.id}`}>{reading.device.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${reading.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${reading.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${reading.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="jhipsterSampleApplicationApp.reading.home.notFound">No Readings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ reading }: IRootState) => ({
  readingList: reading.entities,
  loading: reading.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Reading);
