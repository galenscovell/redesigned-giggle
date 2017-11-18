package galenscovell.sandbox.states;

import galenscovell.sandbox.entities.components.StateComponent;


public enum PlayerAgent implements State<StateComponent> {
    DEFAULT() {
        @Override
        public void enter(StateComponent stateComponent) {
            System.out.println(String.format("Player: Enter %s", getName()));
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println(String.format("Player: Exit %s", getName()));
        }

        @Override
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "DEFAULT";
        }

        @Override
        public int getId() {
            return 0;
        }
    }
}