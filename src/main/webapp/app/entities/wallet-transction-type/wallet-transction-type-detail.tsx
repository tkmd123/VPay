import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallet-transction-type.reducer';
import { IWalletTransctionType } from 'app/shared/model/wallet-transction-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletTransctionTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WalletTransctionTypeDetail extends React.Component<IWalletTransctionTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { walletTransctionTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            WalletTransctionType [<b>{walletTransctionTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="walletTransTypeCode">Wallet Trans Type Code</span>
            </dt>
            <dd>{walletTransctionTypeEntity.walletTransTypeCode}</dd>
            <dt>
              <span id="walletTransTypeName">Wallet Trans Type Name</span>
            </dt>
            <dd>{walletTransctionTypeEntity.walletTransTypeName}</dd>
            <dt>
              <span id="walletTransTypeDesc">Wallet Trans Type Desc</span>
            </dt>
            <dd>{walletTransctionTypeEntity.walletTransTypeDesc}</dd>
            <dt>
              <span id="walletTransTypeFlag">Wallet Trans Type Flag</span>
            </dt>
            <dd>{walletTransctionTypeEntity.walletTransTypeFlag}</dd>
          </dl>
          <Button tag={Link} to="/entity/wallet-transction-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/wallet-transction-type/${walletTransctionTypeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ walletTransctionType }: IRootState) => ({
  walletTransctionTypeEntity: walletTransctionType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletTransctionTypeDetail);
