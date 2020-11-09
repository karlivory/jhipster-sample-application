import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './current-reading.reducer';
import { ICurrentReading } from 'app/shared/model/current-reading.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICurrentReadingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CurrentReadingDetail = (props: ICurrentReadingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { currentReadingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jhipsterSampleApplicationApp.currentReading.detail.title">CurrentReading</Translate> [
          <b>{currentReadingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="type">
              <Translate contentKey="jhipsterSampleApplicationApp.currentReading.type">Type</Translate>
            </span>
          </dt>
          <dd>{currentReadingEntity.type}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="jhipsterSampleApplicationApp.currentReading.value">Value</Translate>
            </span>
          </dt>
          <dd>{currentReadingEntity.value}</dd>
          <dt>
            <span id="timestamp">
              <Translate contentKey="jhipsterSampleApplicationApp.currentReading.timestamp">Timestamp</Translate>
            </span>
          </dt>
          <dd>
            {currentReadingEntity.timestamp ? (
              <TextFormat value={currentReadingEntity.timestamp} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.currentReading.device">Device</Translate>
          </dt>
          <dd>{currentReadingEntity.device ? currentReadingEntity.device.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/current-reading" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/current-reading/${currentReadingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ currentReading }: IRootState) => ({
  currentReadingEntity: currentReading.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CurrentReadingDetail);
