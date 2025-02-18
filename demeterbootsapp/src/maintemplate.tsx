import React from 'react';
import "./styling/components/components.scss";
import "./styling/maincontent.scss";
import SideMenu from './sidemenu';
import Content from './content';

function MainTemplate() {
  return (
    <div className="main">
      <SideMenu />
      <Content type="card" content="order" data={[]}/>
    </div>
  );
}

export default MainTemplate;
