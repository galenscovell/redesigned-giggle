package galenscovell.sandbox.states;

import galenscovell.sandbox.entities.components.StateComponent;


public enum PlayerAgent implements State<StateComponent> {
    DEFAULT() {
        @Override
        public void enter(StateComponent stateComponent) {

        }

        @Override
        public void exit(StateComponent stateComponent) {

        }

        @Override
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "default";
        }

        @Override
        public int getId() {
            return 0;
        }
    },
    WALK() {
        @Override
        public void enter(StateComponent stateComponent) {

        }

        @Override
        public void exit(StateComponent stateComponent) {

        }

        @Override
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "walk";
        }

        @Override
        public int getId() {
            return 1;
        }
    },
    RUN() {
        @Override
        public void enter(StateComponent stateComponent) {

        }

        @Override
        public void exit(StateComponent stateComponent) {

        }

        @Override
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "run";
        }

        @Override
        public int getId() {
            return 2;
        }
    }
}
